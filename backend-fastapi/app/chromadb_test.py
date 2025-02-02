import chromadb
import json
import os
from dotenv import load_dotenv
from langchain_community.embeddings import SentenceTransformerEmbeddings
from langchain_community.vectorstores import Chroma
from langchain_community.chat_models.solar import SolarChat
from langchain.chains.question_answering import load_qa_chain
from langchain.docstore.document import Document

# .env 파일 로드
load_dotenv()

# Solar-Pro API 키 설정
api_key = os.getenv("UPSTAGE_API_KEY")

# 임베딩 모델 설정
embeddings = SentenceTransformerEmbeddings(model_name="all-MiniLM-L6-v2")

# ChromaDB 클라이언트 설정
chroma_client = chromadb.PersistentClient(path="./backend-fastapi/chroma_db")
collection = chroma_client.get_or_create_collection(name="clothes")

# Solar-Pro LLM 설정
llm = SolarChat(model="solar-1-mini-chat", solar_api_key=api_key)
chain = load_qa_chain(llm, chain_type="stuff", verbose=True)


# 🔹 유사한 아이템 검색 함수 (특정 타입만 검색)
def get_best_match(query_embedding, clothing_type):
    results = collection.query(
        query_embeddings=[query_embedding],
        n_results=5  # 여러 개 가져와서 필터링
    )

    # 검색된 아이템 중에서 `type`이 맞는 첫 번째 항목 선택
    for item in results["metadatas"][0]:
        if item["type"] == clothing_type:
            return item  # 해당 타입의 첫 번째 유사한 항목 반환

    return None  # 적합한 아이템이 없으면 None 반환


# 🔹 LLM을 활용하여 추천 이유 생성
def generate_reason(query, best_match):
    if not best_match:
        return "적절한 추천 아이템을 찾지 못했습니다."

    prompt = f"""
    사용자가 "{query}"을(를) 찾고 있습니다.
    아래 의류가 해당 검색어와 가장 유사합니다:
    - 제품명: {best_match['title']}
    - 스타일: {best_match['style']}
    - 착용 가능 장소: {best_match['occasion']}
    - 소재: {best_match['material']}

    이 제품이 "{query}"과 얼마나 적절한지 간략한 이유를 설명해 주세요.
    """

    doc = [Document(page_content=prompt)]
    response = chain.run(input_documents=doc, question="이 제품이 왜 적합한지 설명해줘.")

    return response


# 🔹 상의 & 하의 각각 추천 함수
def get_recommendation(query):
    query_embedding = embeddings.embed_query(query)

    # `top` 추천
    top_item = get_best_match(query_embedding, "top")

    # `bottom` 추천
    bottom_item = get_best_match(query_embedding, "bottom")

    # 추천 이유 생성
    top_reason = generate_reason(query, top_item) if top_item else "적절한 상의 추천을 찾지 못했습니다."
    bottom_reason = generate_reason(query, bottom_item) if bottom_item else "적절한 하의 추천을 찾지 못했습니다."

    # 최종 추천 JSON 생성
    recommendations = {
        "top": {
            **top_item,
            "reason": top_reason
        } if top_item else None,
        "bottom": {
            **bottom_item,
            "reason": bottom_reason
        } if bottom_item else None
    }

    return recommendations


# 🔹 실행 예시
query = "결혼식에 입고 갈 옷"
recommendation = get_recommendation(query)

# JSON 출력
print(json.dumps(recommendation, indent=4, ensure_ascii=False))
