import chromadb
import json
import os
import shutil
from sentence_transformers import SentenceTransformer

# all-MiniLM-L6-v2 모델 로드
embedding_model = SentenceTransformer("sentence-transformers/all-MiniLM-L6-v2")

# chroma_db 디렉토리 경로
chroma_db_dir = './backend-fastapi/chroma_db'
# chroma_db 디렉토리 내부의 모든 파일 및 서브 디렉토리 삭제
if os.path.exists(chroma_db_dir):
    shutil.rmtree(chroma_db_dir)
    print(f"{chroma_db_dir} 디렉토리와 그 안의 모든 내용이 삭제되었습니다.")
else:
    print(f"{chroma_db_dir} 디렉토리가 존재하지 않습니다.")

# ChromaDB 클라이언트 생성 (로컬 저장)
chroma_client = chromadb.PersistentClient(path="./backend-fastapi/chroma_db")
collection = chroma_client.get_or_create_collection(name="clothes")

# 임베딩 생성 함수
def get_embedding(text):
    return embedding_model.encode(text).tolist()

file_path = 'backend-fastapi/resources/spao_woman.json'

if os.path.exists(file_path):
    with open(file_path, "r", encoding="utf-8") as file:
        content = file.read().strip()

    if not content:
        print("⚠ 파일이 비어 있습니다.")
        json_data = []
    else:
        try:
            json_data = json.loads(content)
            print("✅ JSON 파일 로드 성공!")
        except json.JSONDecodeError:
            print("⚠ JSON 형식 오류!")
            json_data = []
else:
    print(f"⚠ 파일을 찾을 수 없습니다: {file_path}")
    json_data = []

# 기존 데이터를 삭제 후 다시 저장
# collection.delete(ids=[str(item["id"]) for item in json_data])

# 데이터를 ChromaDB에 추가
for item in json_data:
#     text_to_embed = f"{item['title']} {item['type']} {item['occasion']} {item['style']} {item['material']}"
    text_to_embed = f" {item['type']} {item['occasion']}"
    embedding = get_embedding(text_to_embed)

    collection.add(
        ids=[str(item["id"])],
        embeddings=[embedding],
        metadatas=[item]
    )

print("데이터 저장 완료")

# 벡터 데이터 만으로 유사도 검색
# def get_recommendation(query):
#     query_embedding = get_embedding(query)
#
#     # ChromaDB에서 유사한 아이템 검색
#     results = collection.query(
#         query_embeddings=[query_embedding],
#         n_results=1  # 가장 적합한 1개 추천
#     )
#
#     # 검색된 결과가 없으면 반환하지 않음
#     if not results["metadatas"] or not results["metadatas"][0]:
#         return None
#
#     best_match = results["metadatas"][0][0]  # 최적의 제품 선택
#
#     # 추천 이유 생성
#     reason = f"이 제품 '{best_match['title']}'은(는) '{query}'에 적합한 스타일입니다."
#
#     # 최종 추천 JSON 생성
#     recommended_item = best_match.copy()
#     recommended_item["reason"] = reason
#
#     return recommended_item
#
# # 사용 예시
# query = "결혼식에 입고 갈 옷"
# recommendation = get_recommendation(query)
#
# # JSON 출력
# print(json.dumps(recommendation, indent=4, ensure_ascii=False))
