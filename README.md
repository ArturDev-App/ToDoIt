# ToDo App Android

Una aplicación de notas/tareas con autenticación de Google y sincronización en tiempo real usando Firebase.

## 🚧 **Estado del proyecto**

⚠️ **Esta aplicación se encuentra actualmente en desarrollo activo.**

- Pueden presentarse errores o funciones no implementadas completamente
- Se realizarán actualizaciones graduales con nuevas características
- Algunas funcionalidades están en fase de prueba
- La interfaz y funciones pueden cambiar en futuras versiones

## 📱 **Demo de la aplicación**

### 🔐 Inicio de sesión
![Login](screenlogin.gif)

### 📝 Crear nueva nota
![Crear nota](Crear_nota.gif)

### ✏️ Actualizar nota existente
![Actualizar nota](actualizacion_de_nota.gif)

### 🗑️ Eliminar nota con swipe
![Eliminar nota](eliminar_nota.gif)

## 🚀 Características

### ✅ **Implementadas:**
- ✅ Autenticación con Google
- ✅ Notas sincronizadas en tiempo real con Firestore
- ✅ CRUD completo de notas (Crear, Leer, Actualizar, Eliminar)
- ✅ Estados de tarea (Pendiente, En Progreso, Completado)
- ✅ Prioridades (Alta, Media, Baja)
- ✅ Categorías/Etiquetas
- ✅ Interfaz moderna con Jetpack Compose
- ✅ Swipe to delete (deslizar para eliminar)

### 🚧 **En desarrollo:**
- 🔨 Notificaciones programables
- 🔨 Búsqueda y filtros avanzados
- 🔨 Temas oscuro/claro
- 🔨 Backup y restauración
- 🔨 Optimizaciones de rendimiento

### 📋 **Planeadas:**
- 📅 Recordatorios con notificaciones
- 📊 Estadísticas y reportes
- 🎨 Personalización de temas
- 📤 Exportación de datos
- 👥 Colaboración entre usuarios

## 🛠️ Configuración del proyecto

### Prerrequisitos
- Android Studio Hedgehog o superior
- SDK de Android 24+
- Cuenta de Google/Firebase

### Configuración inicial

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/ArturDev-App/ToDOIT.git
   cd ToDOIT
   ```

2. **Configurar Firebase**
   - Ve a [Firebase Console](https://console.firebase.google.com/)
   - Crea un nuevo proyecto o usa uno existente
   - Agrega una app Android con el package name: `com.example.todoit`
   - Descarga el archivo `google-services.json`
   - Colócalo en la carpeta `app/`

3. **Configurar Google Sign-In**
   - En Firebase Console → Authentication → Sign-in method
   - Habilita "Google" como proveedor
   - Obtén tu SHA-1 de debug:
     ```bash
     ./gradlew signingReport
     ```
   - Agrega el SHA-1 en Firebase Console → Project Settings → Your apps

4. **Configurar local.properties**
   Crea/edita el archivo `local.properties` en la raíz del proyecto:
   ```properties
   sdk.dir=ruta/a/tu/android/sdk
   default_web_client_id=TU_WEB_CLIENT_ID_AQUI
   ```
   
   **Nota**: Encuentra tu Web Client ID en el archivo `google-services.json` bajo `oauth_client` con `client_type: 3`

5. **Configurar Firestore**
   - En Firebase Console → Firestore Database
   - Crear base de datos en modo de prueba
   - Configurar reglas de seguridad según necesidades

### Estructura del proyecto

```
app/
├── src/main/java/com/example/todoit/
│   ├── model/              # Modelos de datos
│   ├── presentacion/
│   │   ├── components/     # Componentes reutilizables
│   │   ├── screens/        # Pantallas de la app
│   │   └── navegation/     # Navegación
│   └── viewmodel/          # ViewModels
```

## 🔧 Tecnologías utilizadas

- **Jetpack Compose** - UI moderna y declarativa
- **Firebase Auth** - Autenticación segura
- **Firestore** - Base de datos en tiempo real
- **Navigation Compose** - Navegación entre pantallas
- **Material Design 3** - Diseño y componentes UI
- **Kotlin** - Lenguaje de programación principal
- **MVVM Architecture** - Patrón de arquitectura

## 🐛 Problemas conocidos

- [ ] Algunas animaciones pueden presentar lag en dispositivos antiguos
- [ ] La sincronización offline está en desarrollo
- [ ] Algunos temas visuales necesitan pulimiento

## 🔄 Historial de versiones

### Versión actual: 1.0.0-dev
- ✅ Funcionalidades básicas implementadas
- ✅ Autenticación con Google funcional
- ✅ CRUD de notas operativo
- 🚧 En desarrollo activo

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Este proyecto está en desarrollo activo.

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`)
3. Commit tus cambios (`git commit -m 'Agregar nueva característica'`)
4. Push a la rama (`git push origin feature/nueva-caracteristica`)
5. Abre un Pull Request

### 📝 Reportar errores
Si encuentras errores o tienes sugerencias:
1. Ve a la sección **Issues**
2. Describe el problema detalladamente
3. Incluye pasos para reproducir el error
4. Agrega capturas de pantalla si es posible

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## ⚠️ Notas importantes de seguridad

- No subas el archivo `google-services.json` al repositorio
- El archivo `local.properties` está en `.gitignore` por seguridad
- Asegúrate de configurar tus propias credenciales de Firebase
- Revisa las reglas de Firestore antes de desplegar en producción

## 📞 Contacto

**Desarrollador**: ArturDev-App
**GitHub**: [@ArturDev-App](https://github.com/ArturDev-App)

---

**⚠️ Disclaimer**: Esta aplicación está en desarrollo activo. Úsala bajo tu propio riesgo. Los datos pueden perderse durante las actualizaciones en versiones de desarrollo.
