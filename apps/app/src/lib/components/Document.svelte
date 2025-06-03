<script lang="ts">
	import Icon from '@iconify/svelte';
	import Tag from './Tag.svelte';
	let { document } = $props();
	let numberFiles = document.files.length;
</script>

<section class="document-wrapper">
	{#if numberFiles !== 0}
		<img src="/files/{document.files[0].id}/preview" alt="" />
		<p class="title">{document.title}</p>
		<div class="infos">
			<div class="info">
				<div class="info-icon">
					<Icon icon="heroicons:folder" />
				</div>
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
				<div class="info-icon">
					<Icon icon="heroicons:user-circle" />
				</div>
				<p>{document.author.firstname} {document.author.lastname}</p>
			</div>
			<div class="info tags">
				{#each document.tags as tag (tag.id)}
					<Tag {tag} />
				{/each}
			</div>
		</div>
		<div class="actions">
			<a class="button" href="/files/{document.files[0].id}">
				<Icon icon="heroicons:document-chart-bar" />
			</a>
			<a class="button middle" href="/files/{document.files[0].id}">
				<Icon icon="heroicons:eye" />
			</a>
			<a class="button" href="/files/{document.files[0].id}">
				<Icon icon="heroicons:arrow-down-tray-solid" />
			</a>
		</div>
	{:else}
		<p>{document.id} pas de files</p>
	{/if}
</section>

<style lang="scss">
	.document-wrapper {
		display: flex;
		flex-direction: column;
		background-color: #f8f9fa;
		border: solid 1px #e6e6e6;
		border-radius: 0.5rem;
		box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
		width: 12rem;
		overflow: hidden;

		img {
			width: 100%;
			height: 10rem;
			object-fit: cover;
			object-position: top;
		}

		.title {
			display: inline-block;
			box-sizing: border-box;
			padding: 0.5rem;
			border-bottom: solid 1px #e6e6e6;
			color: #212529;
			overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
		}

		.infos {
			display: flex;
			flex-direction: column;
			padding: 0.5rem;
			gap: 0.25rem;
			font-size: 0.9rem;
			color: #212529;

			.info {
				display: flex;
				align-items: center;

				.info-icon {
					display: flex;
					justify-content: center;
					align-items: center;
					margin-right: 0.5rem;
					height: 100%;
				}
			}

			.tags {
				margin-top: 0.5rem;
				flex-wrap: wrap;
				gap: 0.25rem;
				font-size: 0.7rem;
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

			.button {
				display: flex;
				justify-content: center;
				align-items: center;
				flex: 1;
				border: none;
				background: none;
				padding: 0.25rem 0;
				cursor: pointer;
				color: #66686b;
				transition: background-color 0.2s ease;

				&:hover {
					background-color: #989898;
					color: #f8f9fa;
				}
			}

			.middle {
				border-left: solid 1px #66686b;
				border-right: solid 1px #66686b;
			}
		}
	}
</style>
