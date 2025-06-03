<script lang="ts">
	import { onMount } from 'svelte';
	import Header from '$components/Header.svelte';
	import PdfViewer from '$components/PdfViewer.svelte';
	import Tag from '$components/Tag.svelte';
	import Icon from '@iconify/svelte';
	import { Button } from 'bits-ui';
	import { goto } from '$app/navigation';

	let { data } = $props();
	let document = data.document ?? {};
	let nbFiles = (document.files ?? []).length;
	let currentFile: number = $state(0);
	let currentFileId = $state((document.files ?? [])[0].id);

	let comments = document.comments ?? [];

	function updateFile(direction: string) {
		if (direction === 'next') currentFile = (currentFile + 1) % nbFiles;
		else if (direction === 'prev') {
			if (currentFile == 0) currentFile = nbFiles - 1;
			else currentFile--;
		}
		currentFileId = (document.files ?? [])[currentFile].id;
	}

	// Make sure the viewer don't overflow from the screen.
	// More user friendly
	let viewer: HTMLElement;

	function resizeDiv() {
		const topOffset = viewer?.getBoundingClientRect().top || 0;
		viewer.style.height = `calc(100vh - ${topOffset}px - 1rem)`;
	}

	onMount(() => {
		resizeDiv();
		window.addEventListener('resize', resizeDiv);

		return () => {
			window.removeEventListener('resize', resizeDiv);
		};
	});
</script>

<section class="document-wrapper">
	<section class="infos">
		<Header title="Document" />
		<h2 class="title">{document.title}</h2>
		<p class="description">{document.description}</p>
		<div class="info">
			<div class="info-icon">
				<Icon icon="heroicons:academic-cap" />
			</div>
			<p>{document.subject?.name}</p>
		</div>
		<div class="info">
			<div class="info-icon">
				<Icon icon="heroicons:folder" />
			</div>
			<p>
				{nbFiles}
				{#if nbFiles <= 1}
					fichier
				{:else}
					fichiers
				{/if}
			</p>
		</div>
		<div class="info">
			<div class="info-icon">
				<Icon icon="heroicons:user-circle" />
			</div>
			<p>{(document.author ?? {}).firstname} {(document.author ?? {}).lastname}</p>
		</div>
		<div class="info">
			<div class="info-icon">
				<Icon icon="heroicons:calendar-solid" />
			</div>
			<p>{document.year}</p>
		</div>
		<div class="info tags">
			{#each document.tags ?? [] as tag (tag.id)}
				<Tag {tag} />
			{/each}
		</div>

		<div class="button-nav">
			<Button.Root onclick={() => goto('../')}
				><Icon icon="heroicons:arrow-left-solid" />Retour</Button.Root
			>
			<Button.Root><Icon icon="heroicons:arrow-down-tray-solid" />Télécharger</Button.Root>
		</div>

		<div class="comments">
			<div class="comment-input">
				<input type="text" class="input" required />
				<Button.Root form="comment-form"
					><Icon icon="heroicons:arrow-down-solid" /></Button.Root
				>
			</div>
			{#each comments as comment (comment.id)}
				<div class="comment">
					<img
						src={`https://churros.inpt.fr/${(comment.user ?? {}).uid}.png`}
						alt={(comment.user ?? {}).uid}
					/>
					<p class="comment-text">
						{comment.content}
					</p>
				</div>
			{/each}
		</div>
	</section>
	<section class="pdf-nav" bind:this={viewer}>
		{#key currentFileId}
			<PdfViewer fileID={currentFileId} />
		{/key}
		<div class="files-nav">
			<button class="left" onclick={() => updateFile('prev')}>
				<Icon icon="heroicons:chevron-left-solid" />
			</button>
			<p>Fichier {currentFile + 1} / {nbFiles}</p>
			<button class="right" onclick={() => updateFile('next')}>
				<Icon icon="heroicons:chevron-right-solid" />
			</button>
		</div>
	</section>
</section>

<style lang="scss">
	%border-pad {
		border: solid 1px #6c6e71;
		border-radius: 5px;
		padding: 0.5rem 1rem;
	}

	.document-wrapper {
		display: grid;
		grid-template-columns: 1fr 2fr;
		gap: clamp(1rem, 4vw, 4rem);
		width: 100%;

		.infos {
			display: flex;
			flex-direction: column;
			margin: 1rem 0 0 1rem;

			.title {
				font-size: 1.25rem;
				margin: 1rem 0;
				margin-bottom: 0.25rem;
			}

			.description {
				margin-bottom: 1rem;
			}

			.info {
				display: flex;
				align-items: center;
				color: #646464;

				.info-icon {
					display: flex;
					justify-content: center;
					align-items: center;
					margin-right: 0.5rem;
					height: 100%;
				}

				p {
					color: inherit;
				}
			}

			.tags {
				margin-top: 0.5rem;
				padding-bottom: 2rem;
				flex-wrap: wrap;
				gap: 0.25rem;
				font-size: 0.8rem;
			}

			.button-nav {
				display: flex;
				gap: 0.5rem;
				padding-bottom: 1rem;
				border-bottom: solid 1px #6c6e71;
			}

			.comments {
				display: flex;
				flex-direction: column;
				gap: 1rem;
				margin: 1rem 0;

				.comment-input {
					display: flex;
					justify-content: center;
					gap: 0.5rem;
					width: 100%;

					.input {
						font-size: 1rem;
						padding: 0.5rem;
						width: 100%;
						border: solid 1px #e6e6e6;
						border-radius: 0.25rem;
						color: #212529;
					}
				}

				.comment {
					display: flex;

					.comment-text {
						@extend %border-pad;
						border-color: #e6e6e6;
						color: #525252;
					}

					img {
						width: 2rem;
						height: 2rem;
						border-radius: 50%;
						margin-right: 0.5rem;
						margin-top: 0.25rem;
					}
				}
			}
		}

		.pdf-nav {
			display: flex;
			flex-direction: column;
			align-items: center;
			position: sticky;
			// margin-bottom: 1rem;
			top: 1rem;
			gap: 1rem;
			width: 100%;

			.files-nav {
				display: flex;
				position: absolute;
				bottom: 1rem;

				button {
					@extend %border-pad;
					display: flex;
					justify-content: center;
					align-items: center;
					background-color: #f8f9fa;
					cursor: pointer;
					box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);

					&:hover {
						background-color: #989898;
						color: #f8f9fa;
					}
				}

				.left {
					border-top-right-radius: 0;
					border-bottom-right-radius: 0;
				}

				.right {
					border-top-left-radius: 0;
					border-bottom-left-radius: 0;
				}

				p {
					@extend %border-pad;
					background-color: #f8f9fa;
					box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
					border-radius: 0;
					border-left: none;
					border-right: none;
				}
			}
		}
	}
</style>
