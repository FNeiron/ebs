import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import {FRONT_PORT} from './config'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],

  server: {
    port: FRONT_PORT
  }
})
