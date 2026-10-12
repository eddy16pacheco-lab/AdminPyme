ADMINPYME - VERSION DEMO
========================

Version de demostracion que funciona SIN base de datos y SIN servidor real.
Todos los datos (productos, ventas, clientes, creditos, compras, gastos...)
son de ejemplo y viven unicamente en el navegador (localStorage).

COMO EJECUTARLA
---------------
Requisito: Node.js 18 o superior.

  Windows : doble clic en  iniciar-demo.bat
  Otros   : node servidor-demo.js      (luego abrir http://localhost:5000)

USUARIOS DE PRUEBA (clave: demo123)
-----------------------------------
  gerente@demo.com    -> acceso total (todos los modulos Premium desbloqueados)
  empleado@demo.com   -> solo ventas, productos y su cierre de caja
  (En la pantalla de login hay botones para entrar con un clic.)

COMO FUNCIONA
-------------
- Los cambios que hagas (ventas, productos, pagos...) se guardan en este
  navegador y se mantienen al recargar la pagina.
- Boton "Reiniciar demo" (barra superior): borra tus cambios y restaura los
  datos de ejemplo originales.
- Los datos son por navegador: otro navegador u otro equipo empieza de cero.
- Las exportaciones a Excel/PDF y el lector de codigo de barras USB
  funcionan igual que en la version real.

DIFERENCIAS CON LA VERSION REAL
-------------------------------
- Sin notificaciones en tiempo real entre usuarios (no hay servidor).
- Sin consulta automatica de la tasa BCV por internet (se edita a mano).
- Plan Premium activado de fabrica para mostrar todas las funciones.

PUBLICARLA EN INTERNET (opcional)
---------------------------------
La carpeta "app" es un sitio 100% estatico: se puede subir tal cual a
Netlify, Vercel, GitHub Pages, etc. (incluye _redirects para Netlify).
