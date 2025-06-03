<script lang="ts">
	import { goto } from '$app/navigation';
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	async function create(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const title = formData.get('title')?.toString() ?? '';
		const year = Number(formData.get('Year')?.toString());
		const description = formData.get('description')?.toString() ?? '';
		const subject_id = Number(formData.get('subject_id')?.toString());
		const tags = formData.getAll('tags').map((id) => Number(id));
		const files = formData.getAll('files') as File[];

		const newDocument = await client.POST('/document/', {
			body: {
				title,
				year,
				description,
				subject_id,
				tags
			}
		});

		const documentId = newDocument.data?.id;
		if (!documentId) throw new Error('Error creating document');

		for (const file of files) {
			if (file.size === 0) continue;

			await client.POST('/document/{id}/upload', {
				params: {
					path: { id: documentId }
				},
				body: {
					file: file as unknown as string
				},
				bodySerializer: (body) => {
					if (!body) return null;
					const formData = new FormData();
					formData.set('file', body.file);
					return formData;
				}
			});
		}

		goto('./');
	}
</script>

<h3>Create</h3>
<form onsubmit={create}>
	<input type="text" name="title" placeholder="Title" required />
	<input type="number" name="Year" value={new Date().getFullYear()} required />
	<input type="text" name="description" placeholder="Description" />
	<select name="subject_id" required>
		{#each data.subjects as subject (subject.id)}
			<option value={subject.id}>{subject.name}</option>
		{/each}
	</select>
	<select name="tags" multiple>
		<option value="" disabled>Select tags</option>
		{#each data.tags as tag (tag.id)}
			<option value={tag.id}>{tag.name}</option>
		{/each}
	</select>

	<input type="file" name="files" multiple />

	<button type="submit">Create</button>
</form>
<br />
<br />
<button onclick={() => goto('./')}> Go back </button>
