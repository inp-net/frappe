import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	plugins: [sveltekit()],
	optimizeDeps: {
		include: ['pdfjs-dist/build/pdf']
	},
	build: {
		rollupOptions: {
			output: {
				inlineDynamicImports: false
			}
		}
	},
	css: {
		preprocessorOptions: {
			scss: {
				additionalData: `@use '$styles/global.scss' as *;`
			}
		}
	},
	resolve: {
		alias: {
			$styles: '/src/lib/styles'
		}
	}
});
