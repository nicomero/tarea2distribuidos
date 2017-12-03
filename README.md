# tarea2distribuidos


Nicolás Alarcón
Rodrigo Elicer

## Intrucciones

Ejecutar los siguientes comandos en ubuntu

Para iniciar el registro
> $ rmiregistry &

Para compilar el codigo
> $ make

Para ejecutar el servidor
> $ make run MAIN=Servidor

Para ejecutar un proceso de la forma
```
process <id> <n> <initialDelay> <Bearer>
```
donde
> $ make run ID=<id> N=<n> DELAY=<initialDelay> BEARER=<Bearer>

## Estrategia

El progrma implementa el algoritmo suzuki-kasami, en el cual se controla el acceso a secciones criticas (CS) por procesos remotos mediante el paso de un token.
