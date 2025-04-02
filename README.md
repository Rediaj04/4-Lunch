# 📝 TNote - Tu Gestor de Notas Personal

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Rediaj04/TNote)

</div>

## 🌟 Descripción

TNote es una aplicación de gestión de notas desarrollada en Java que te permite organizar tus ideas y tareas de manera eficiente. Diseñada con una interfaz de consola intuitiva, te ofrece un control total sobre tus notas con la capacidad de crear, editar, eliminar y organizar tus notas según tus necesidades.

## ✨ Características

- 🔐 Acceso personalizado por usuario
- 📋 Creación y gestión de notas
- 🏷️ Estados personalizables para tus notas
- 🔄 Edición completa de notas
- 🗑️ Eliminación segura de notas
- 📊 Visualización organizada
- 💾 Persistencia de datos con MongoDB

## 🚀 Tecnologías Utilizadas

- Java
- Spring Boot
- MongoDB
- Maven
- Lombok

## 🛠️ Requisitos Previos

- Java 17 o superior
- MongoDB instalado y ejecutándose
- Maven

## 📦 Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/Rediaj04/TNote.git
```

2. Navega al directorio del proyecto:
```bash
cd TNote
```

3. Compila el proyecto:
```bash
mvn clean install
```

4. Ejecuta la aplicación:
```bash
mvn spring-boot:run
```

## 🔧 Modo Prueba

Para ejecutar la aplicación en modo prueba sin MongoDB:

```bash
mvn spring-boot:run "-Dspring-boot.run.profiles=test"
```

O alternativamente:
```bash
mvn spring-boot:run -D"spring-boot.run.profiles=test"
```

## 🗄️ Configuración de MongoDB

Para que la aplicación funcione correctamente, es necesario tener MongoDB instalado y ejecutándose:

1. **Instalación de MongoDB**:
   - [Descarga MongoDB Community Server](https://www.mongodb.com/try/download/community)
   - Sigue las instrucciones de instalación para tu sistema operativo

2. **Iniciar MongoDB**:
   - En Windows:
     ```bash
     net start MongoDB
     ```
   - En Linux/Mac:
     ```bash
     sudo service mongod start
     ```

3. **Verificar la conexión**:
   - MongoDB debe estar ejecutándose en el puerto por defecto (27017)
   - La aplicación se conectará automáticamente a la base de datos "notesdb"

## ⚠️ Errores Comunes y Soluciones

### 1. Error de conexión a MongoDB
```
Failed to connect to MongoDB: Connection refused
```
**Solución**: 
- Verifica que MongoDB esté instalado y ejecutándose
- Asegúrate de que el servicio de MongoDB esté activo
- Comprueba que el puerto 27017 esté disponible

### 2. Error de compilación Maven
```
Failed to execute goal org.springframework.boot:spring-boot-maven-plugin
```
**Solución**:
- Verifica que tienes Java 17 o superior instalado
- Ejecuta `mvn clean` antes de intentar compilar nuevamente
- Asegúrate de tener todas las dependencias descargadas

### 3. Error de permisos
```
Access denied to database
```
**Solución**:
- Verifica que MongoDB esté configurado correctamente
- Asegúrate de que el usuario tenga los permisos necesarios
- Comprueba la configuración en `application.properties`

### 4. Error de memoria
```
OutOfMemoryError: Java heap space
```
**Solución**:
- Aumenta la memoria disponible para Java
- Ejecuta la aplicación con: `java -Xmx2g -jar target/tnote.jar`

## 👥 Creadores

<div align="center">

[![SoyManoolo](https://img.shields.io/badge/SoyManoolo-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/SoyManoolo)
[![Rediaj04](https://img.shields.io/badge/Rediaj04-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Rediaj04)

</div>

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

---

<div align="center">
Hecho con ❤️ por SoyManoolo y Rediaj04
</div>

