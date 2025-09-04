# eSanar REST API
**Sistema de gestión médica para clínicas de heridas**

## 📖 Descripción del Proyecto
eSanar es un proyecto académico desarrollado en **Java con Spring Boot** que busca sistematizar los procesos de una clínica de heridas, permitiendo la **gestión integral de pacientes**, el almacenamiento de **historias clínicas**, registro de **consultas diarias**, y la **generación de reportes médicos**.

Originalmente, eSanar se desarrolló como una **aplicación web con arquitectura MVC** utilizando **Thymeleaf** como motor de plantillas.  
En la etapa actual (semestre 2025-2), el proyecto está siendo **refactorizado** para convertirse en una **REST API**, mejorando su escalabilidad, mantenibilidad y alineándose con buenas prácticas de desarrollo.

Esta versión forma parte de la materia **Codificación y Pruebas de Software** de la carrera de **Ingeniería de Software**, con un enfoque en:
- Refactorización de código y optimización de entidades, servicios y controladores.
- Implementación de una arquitectura limpia basada en APIs REST.
- Integración de herramientas de colaboración como **GitHub** y **Docker** para ambientes de desarrollo y despliegue.
- Pruebas unitarias y funcionales para garantizar la calidad del software.

---

## 🎯 Objetivos de la Etapa Actual
- Migrar la aplicación de un modelo **MVC tradicional** a un **servicio RESTful**.
- Definir y documentar endpoints para la gestión de pacientes, historias clínicas y consultas.
- Mejorar la estructura de paquetes y aplicar principios SOLID y buenas prácticas de diseño.
- Crear un entorno reproducible con **Docker** para la base de datos y el backend.
- Integrar control de versiones mediante **GitHub**, facilitando la colaboración en equipo.

---

## 🛠️ Tecnologías y Herramientas
- **Java 21** – Lenguaje principal para el backend.
- **Spring Boot** – Framework para el desarrollo del API.
- **Postgresql** – Base de datos relacional.
- **Docker y Docker Compose** – Contenerización de la base de datos y entorno de desarrollo.
- **Git y GitHub** – Control de versiones y colaboración en equipo.
- **Maven** – Gestión de dependencias y construcción del proyecto.
- **JUnit y Mockito** – Pruebas unitarias y de integración.

---

## 📚 Documentación Relacionada
- [📄 Proyecto Pedagógico de Aula (PPA)](https://1drv.ms/w/c/5be010ee67a3ad31/EekLPyiJbGhOsDlz2dB8dosB2LUFnpJCnMpfGo0bDKq2Cg?e=21oOci) – Documento base del proyecto con introducción, objetivos y marco teórico.
- [📑 Documento de Requerimientos de Software](https://1drv.ms/w/c/5be010ee67a3ad31/EaBrYaO4Ys1OgGJVHuI-mMYBz9EpVzdAwGs9Sevy_liDyQ?e=z28KRP) – Contiene los requerimientos funcionales y no funcionales.
- [📑 Documento Ejecutivo](https://drive.google.com/file/d/1zJwgxlXrEZ5DHEV-tyYnmA0Qu-1cM55R/view?usp=sharing) – Presenta y contextualiza esta nueva fase del proyecto a la materia de Codificacion y Purebas de software.

---

## ⚙️ Funcionalidades Planeadas para la REST API
- Gestión de pacientes:
    - Registro y actualización de pacientes.
    - Generación automática de historias clínicas.
- Gestión de consultas:
    - Registro de consultas diarias vinculadas a historias clínicas.
    - Actualización y consulta de registros.
- Reportes:
    - Generación de reportes en formato PDF.
    - Exportación de pacientes a Excel.
- Integración con servicios externos:
    - Consumo de la API **CIE-10** de la OMS.
- Seguridad:
    - Autenticación y autorización basada en roles y permisos.

