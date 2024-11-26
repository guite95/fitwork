import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    // host: '0.0.0.0', // 외부에서 접근할 수 있도록 모든 호스트에 바인딩
    // port: 5173, // 사용할 포트 번호
    proxy: {
      '/api-admin': {
        target: 'http://localhost:8080', // 백엔드 서버 주소 (Spring Boot 서버)
        changeOrigin: true, // 대상 서버의 Origin을 변경
        rewrite: (path) => path.replace(/^\/api-admin/, '/api-admin'), // 프록시 경로 재작성 (필요한 경우)
      },
    },
  },
});
