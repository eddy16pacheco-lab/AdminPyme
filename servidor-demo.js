// Servidor estatico minimo para la DEMO de AdminPyme.
// No usa base de datos ni dependencias: solo sirve los archivos de ./app
// (con fallback a index.html para que funcionen las rutas de la app).
import http from 'node:http'
import fs from 'node:fs'
import path from 'node:path'
import { fileURLToPath } from 'node:url'

const raiz = path.join(path.dirname(fileURLToPath(import.meta.url)), 'app')
const PORT = process.env.PORT || 5000

const tipos = {
  '.html': 'text/html; charset=utf-8', '.js': 'text/javascript; charset=utf-8',
  '.css': 'text/css; charset=utf-8', '.json': 'application/json', '.png': 'image/png',
  '.jpg': 'image/jpeg', '.svg': 'image/svg+xml', '.ico': 'image/x-icon',
  '.webmanifest': 'application/manifest+json', '.woff2': 'font/woff2'
}

const servir = (res, archivo) => {
  const ext = path.extname(archivo).toLowerCase()
  res.writeHead(200, { 'Content-Type': tipos[ext] || 'application/octet-stream' })
  fs.createReadStream(archivo).pipe(res)
}

http.createServer((req, res) => {
  const url = decodeURIComponent((req.url || '/').split('?')[0])
  const archivo = path.normalize(path.join(raiz, url))
  if (!archivo.startsWith(raiz)) { res.writeHead(403); return res.end('Prohibido') }
  fs.stat(archivo, (err, st) => {
    if (!err && st.isFile()) return servir(res, archivo)
    servir(res, path.join(raiz, 'index.html')) // fallback SPA
  })
}).listen(PORT, () => {
  console.log(`AdminPyme DEMO escuchando en http://localhost:${PORT}`)
  console.log('Usuarios de prueba: gerente@demo.com / empleado@demo.com  (clave: demo123)')
})
