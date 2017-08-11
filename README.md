# Control de inventario Multipunto CL
Manejo de inventario, control: entradas y salidas de productos. Conectividad para cada punto estratégico que requiera.
Jerarquia de establecimientos
* Central de control (Cerebro)
* Central de abastecimiento (Bodega)
* Sucursal de venta (Tienda)

## Manejo de ramas en repositorio
* Master -> Rama con los cambios revisados y validados para integración de un producto ESTABLE.
```
git push -u origin master
```
* dev    -> Rama para desarrollo (development) cambios INESTABLES y/o en proceso para un producto terminado. 
```
git push -u origin dev
```
* maintenance -> Labores de mantenimiento : Debe derivar o contener los cambios de la rama Master antes de comenzar con dichas actividades.
```
git push -u origin maintenance
```

## Requisitos
* IDE : NetBeans.
* JDK : 1.8.0_x.
* SceneBuilder : JavaFX.
* Hibernate <- librerias ya incluidas en proyecto.

### Colaboradores
for name in names: write name 
* Andres Cruz Aguilar 
* Arturo Cordero Muñiz
* Carlos Maximiliano Ortiz Escobar
* Elihu A. Cruz Albores
* Luis Ángel Farelo Toledo
* Victor Fernando Gil Calderón

© All rights reserved CandleLabs Technologies."# InventoryControl" 
