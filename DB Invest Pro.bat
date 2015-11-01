@echo off 

mysql -u dev -p -e "DROP DATABASE IF EXISTS investPro"

echo investPro database deleted

mysql -u dev -p -e "CREATE DATABASE IF NOT EXISTS investPro"

echo investPro database created