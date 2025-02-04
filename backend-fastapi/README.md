# 환경설정

# LLM기반 의류 추천 시스템

## 파이썬 - Python, Fastapi
### 정수경 - 스크래핑
1. 타사 스크래핑
    1. 아이디 + 타입(상하의) + 성별 + 제품명 + 가격 + 색상 + 사이즈 + 소재 + 이미지주소 + URL
    2. 행사(occasion)+ 스타일(style)

### 박영제 - LLM
1. LLM(Solar pro)
2. RAG

## 새 가상환경 생성
> python -m venv .venv
### 가상환경 활성화 (Windows)
> .venv\Scripts\activate
### 가상환경 활성화 (Mac/Linux)
> source .venv/bin/activate

* 아래 설치는 터미널(.venv)에서 실행하세요

`python -m pip install --upgrade pip`

`pip install fastapi[standard]`
`pip install python-dotenv`

`pip install langchain`
`pip install langchain_core`
`pip install langchain_upstage`
`pip install langchain_community`

`pip install chromadb`
`pip install sentence-transformers`

`pip install -U langchain-huggingface`
`pip install -U transformers tokenizers `
`pip install -U langchain-upstage`

### ChromaDB 저장
> python .\backend-fastapi\app\chromadb_storage.py 

### FastAPI 서버 실행
> fastapi dev --no-reload .\backend-fastapi\app\recommend_chromadb.py
- 취소 : Ctrl + C

---

# 최종 배포
0) 로컬서버 - 현재 완료
1) 상용서버(EC2) - 목표
2) CICD(TravisCI + S3 + Nginx) - 희망사항