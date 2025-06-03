<script lang="ts">
	let { fileID } = $props();
	let pdfUrl = $state(`../files/${fileID}`);

	import { onMount } from 'svelte';

	let iframeEl;

	onMount(() => {
		iframeEl = document.getElementById('viewer') as HTMLIFrameElement;
		if (iframeEl && iframeEl.contentWindow && iframeEl.contentDocument) {
			const iframeDoc = iframeEl.contentDocument;

			iframeDoc.body.style.display = 'flex';
			iframeDoc.body.style.justifyContent = 'center';
			iframeDoc.body.style.alignItems = 'center';
			iframeDoc.body.style.height = '100vh';
		}
	});
</script>

<iframe id="viewer" src={pdfUrl} title="PDF Viewer"></iframe>

<style lang="scss">
	iframe {
		width: 100%;
		height: 100%;
		border: solid 1px #6c6e71;
		border-radius: 5px;
	}
</style>
