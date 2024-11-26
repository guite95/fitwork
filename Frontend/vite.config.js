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
  // server: {
  //   host: '0.0.0.0', // 외부에서 접근할 수 있도록 모든 호스트에 바인딩
  //   port: 5173, // 사용할 포트 번호 (필요에 따라 변경 가능)
  // },
});
