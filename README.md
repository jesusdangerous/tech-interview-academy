# Tech Interview Academy 🎓💻

Приложение для подготовки к техническим собеседованиям (в виде статей). Содержит backend на Spring Boot, frontend на React и поддержку Kafka, PostgreSQL, Prometheus и Grafana.

---

## 📦 Стек технологий

- **Backend:** Java Spring Boot
- **Frontend:** React
- **Database:** PostgreSQL
- **Broker:** Apache Kafka + Zookeeper
- **Monitoring:** Prometheus + Grafana
- **Email-сервис:** SMTP (Yandex)

---

## 🚀 Запуск проекта

### 1. Клонировать репозиторий

```bash
git clone https://github.com/jesusdangerous/tech-interview-academy.git
cd tech-interview-academy
```

> Рекомендуется перед запуском отключить впн (так как почтовый сервер не сможет отправлять сообщения)

### 2. Создать `.env` файл

В корне проекта /academy создайте файл `.env` со следующим содержимым:

```env
POSTGRES_PASSWORD=your_postgres_password
JWT_SECRET_KEY=your_jwt_secret
MAIL_NAME=your_email@yandex.ru
MAIL_PASSWORD=your_email_password
POSTGRES_DB=academy
```

> Замените значения переменных на свои собственные.

### 3. Запуск с помощью Docker

Перейдите в директорию `academy` и выполните команду:

```bash
cd academy
docker-compose up --build
```

---

### 4. Доступ к сервисам

| Сервис       | Адрес                        |
|--------------|------------------------------|
| Backend API  | http://localhost:8080        |
| Frontend     | http://localhost:5173        |
| Adminer      | http://localhost:8080        |
| Prometheus   | http://localhost:8090        |
| Grafana      | http://localhost:3000        |
| Kafka        | `kafka:9092` (внутри docker-сети) |

---

## 📂 Структура проекта

```
tech-interview-academy/
├── academy/             # Backend + docker-compose
│   ├── src/             # Исходники Spring-приложения
│   ├── Dockerfile
│   └── docker-compose.yml
├── frontend/            # React-проект
├── .env                 # Конфигурация окружения
└── README.md
```

---

## ✅ Переменные окружения

| Переменная         | Назначение                         |
|--------------------|------------------------------------|
| `POSTGRES_PASSWORD`| Пароль для базы данных PostgreSQL |
| `POSTGRES_DB`      | Название базы данных              |
| `JWT_SECRET_KEY`   | Секретный ключ для JWT токенов    |
| `MAIL_NAME`        | Email отправителя                 |
| `MAIL_PASSWORD`    | Пароль от email                   |

---

## 🛠 Полезные команды

```bash
# Остановить и удалить все контейнеры
docker-compose down

# Перезапустить backend с пересборкой
docker-compose up --build
```

---

## 📫 Контакты

Проект разработал: [Александр Зонов](https://t.me/whqsfasagd)
