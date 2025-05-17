<script lang="ts">
	import { goto } from '$app/navigation';
	import PdfThumbnail from './PdfThumbnail.svelte';
	let { document } = $props();
	let numberFiles = document.files.length;
</script>

<section class="document-wrapper">
	<PdfThumbnail fileID={document.files[0].id} />
	<p class="title">{document.title}</p>
	<div class="infos">
		<div class="info">
			<span class="material-symbols-outlined">folder</span>
			<p>
				{numberFiles}
				{#if numberFiles <= 1}
					fichier
				{:else}
					fichiers
				{/if}
			</p>
		</div>
		<div class="info">
			<span class="material-symbols-outlined">person</span>
			<p>{document.author.firstname} {document.author.lastname}</p>
		</div>
		<div class="info">
			<span class="material-symbols-outlined">label</span>
			<p>
				{#each document.tags as tag, index (tag.id)}
					{#if index !== document.tags.length - 1}
						{tag.name + ', '}
					{:else}
						{tag.name}
					{/if}
				{/each}
			</p>
		</div>
	</div>
	<div class="actions">
		<button onclick={() => goto(`/documents/${document.id}`)}>
			<span class="material-symbols-outlined">description</span>
		</button>
		<button onclick={() => goto(`/documents/${document.id}`)}>
			<span class="material-symbols-outlined">visibility</span>
		</button>
		<button onclick={() => goto(`/files/${document.files[0].id}`)}>
			<span class="material-symbols-outlined">download</span>
		</button>
	</div>
</section>

<style lang="scss">
	span {
		color: #66686b;
	}

	.document-wrapper {
		display: flex;
		flex-direction: column;
		background-color: #f8f9fa;
		border: solid 1px #e6e6e6;
		border-radius: 0.5rem;
		box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
		width: 12rem;
		overflow: hidden;

		.title {
			display: flex;
			align-items: center;
			padding: 0.5rem;
			border-bottom: solid 1px #e6e6e6;
			color: #212529;
		}

		.infos {
			display: flex;
			flex-direction: column;
			padding: 0.5rem;
			gap: 0.25rem;
			font-size: 0.9rem;
			color: #212529;

			.material-symbols-outlined {
				font-size: 1rem;
				margin-right: 0.5rem;
				height: 100%;
			}

			.info {
				display: flex;
				align-items: center;
			}
		}

		.actions {
			display: flex;
			justify-content: space-between;
			margin: 0.5rem;
			border: solid 1px #66686b;
			border-radius: 0.25rem;
			margin-top: auto;
			overflow: hidden;

			button {
				display: flex;
				justify-content: center;
				align-items: center;
				flex: 1;
				border: none;
				background: none;
				padding: 0.25rem 0;
				cursor: pointer;

				span {
					font-size: 1.2rem;
				}

				&:hover {
					background-color: #66686b;

					span {
						color: #f8f9fa;
					}
				}
			}
		}

		> :first-child {
			border-right: solid 1px #66686b;
		}

		> :last-child {
			border-left: solid 1px #66686b;
		}
	}
</style>
