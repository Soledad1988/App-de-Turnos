# Sistema de Gestión de Turnos Médicos

Este sistema de gestión de turnos médicos permite llevar un registro organizado de los pacientes y sus turnos asignados en un consultorio. Facilita la asignación automática de turnos y brinda una vista clara de los pacientes que están siendo atendidos en un momento dado.

## Instalación

1. Clona o descarga el repositorio desde GitHub.
2. Configura tu base de datos ejecutando los scripts SQL proporcionados en la sección "Estructura de la Base de Datos".
3. Ejecuta la aplicación y sigue las instrucciones en pantalla para empezar a utilizarla.

## Uso

- Ingresa los datos del paciente en el formulario de recepción para asignarle un turno automáticamente.
- Visualiza la lista de pacientes y sus turnos asignados desde la interfaz principal.
- Notifica al especialista mediante planilla excel exportada del sistema los turnos asignados.

## Estructura de la Base de Datos

### Tabla `pacientes`

| Columna       | Tipo       | Descripción                           |
|---------------|------------|---------------------------------------|
| id            | INT        | Identificador único del paciente.     |
| nombre        | VARCHAR(50)| Nombre del paciente.                  |
| apellido      | VARCHAR(200)| Apellido del paciente.                |
| dni           | VARCHAR(150)| Documento Nacional de Identidad del paciente. |
| whatsapp      | VARCHAR(50)| Número de WhatsApp del paciente.      |
| fecha_creacion| DATE       | Fecha de registro del paciente.       |

### Tabla `turnos`

| Columna       | Tipo       | Descripción                           |
|---------------|------------|---------------------------------------|
| idTurno       | INT        | Identificador único del turno.        |
| pacienteId    | INT        | ID del paciente al que se le asigna el turno. |
| turno         | INT        | Número de turno asignado.             |
| fecha         | DATE       | Fecha del turno.                      |

## Ejemplo de Configuración de la Base de Datos

1. Crea una base de datos llamada `gestion_turnos`.
2. Ejecuta los siguientes comandos SQL para crear las tablas:

```sql
CREATE TABLE pacientes(
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(200) NOT NULL,
    dni VARCHAR(150),
    whatsapp VARCHAR(50),
    fecha_creacion DATE,
    PRIMARY KEY(id)
);

CREATE TABLE turnos (
    idTurno INT NOT NULL AUTO_INCREMENT,
    pacienteId INT,
    turno INT,
    fecha DATE,
    PRIMARY KEY (idTurno),
    FOREIGN KEY (pacienteId) REFERENCES pacientes(id)
);
```

  <h4>:clapper: Vista previa :clapper:</h4>

[Turnos.webm](https://github.com/Soledad1988/App-de-Turnos/assets/99606808/dc8ab479-c6f9-4ded-a0ee-150d77503297)

## Tecnologia Utilizada
🔔 Java.
🔔 Java Swing.
🔔 JDBC.
🔔 MySql.


## Autor
<h4>Torres Brenda</h4>

|<img src="https://github.com/Soledad1988/AppGym-JDBC-MySQL-Java/assets/99606808/7889234e-2ef2-4fba-96ad-5ed94b5e6b58" width=115>|
| :---: |
