<script lang="ts">
	import { onMount } from 'svelte';
	import { browser } from '$app/environment';

	let { fileID } = $props();

	let canvas: HTMLCanvasElement;

	onMount(async () => {
		if (!browser || !fileID) return;

		const pdfjs = await import('pdfjs-dist/build/pdf');

		const worker = (await import('$lib/pdf-worker.js')).default;
		pdfjs.GlobalWorkerOptions.workerPort = worker;

		try {
			const response = await fetch(`/files/${fileID}`);
			if (!response.ok)
				throw new Error(`Échec du chargement du fichier: ${response.statusText}`);

			const arrayBuffer = await response.arrayBuffer();

			const pdf = await pdfjs.getDocument({ data: arrayBuffer }).promise;
			const page = await pdf.getPage(1);

			const scale = 0.5;
			const viewport = page.getViewport({ scale });

			const context = canvas.getContext('2d');
			canvas.width = viewport.width;
			canvas.height = viewport.height;

			if (!context) {
				throw new Error('Impossible de récupérer le contexte du canvas');
			}
			await page.render({ canvasContext: context, viewport }).promise;
		} catch (error) {
			console.error('Error loading PDF:', error);
		}
	});
</script>

<canvas bind:this={canvas}></canvas>

<style lang="scss">
	canvas {
		width: 100%;
		height: 10rem;
		object-fit: cover;
	}
</style>
