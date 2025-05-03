#!/bin/bash

# Параметры базы данных
PG_USER="postgres"
PG_PASSWORD="password"
PG_DATABASE="job4j_devops"
PG_HOST="localhost"

# Настройка временной метки и директории для бэкапов
TIMESTAMP=$(date +"%F_%H-%M-%S")
BACKUP_DIR="./backup/output"
mkdir -p "$BACKUP_DIR"

# Имя файла бэкапа
BACKUP_FILE="$BACKUP_DIR/${PG_DATABASE}_$TIMESTAMP.sql"

# Установка пароля в переменную окружения
export PGPASSWORD="$PG_PASSWORD"

# Создание бэкапа
pg_dump -U "$PG_USER" -h "$PG_HOST" "$PG_DATABASE" > "$BACKUP_FILE"

# Проверка статуса
if [ $? -eq 0 ]; then
    echo "Бэкап успешно создан: $BACKUP_FILE"

    # Сжатие файла
    gzip "$BACKUP_FILE"
    if [ $? -eq 0 ]; then
        echo "Бэкап успешно сжат: ${BACKUP_FILE}.gz"
    else
        echo "Ошибка при сжатии"
        exit 1
    fi
else
    echo "Ошибка при создании бэкапа"
    exit 1
fi
