<script lang="ts">
	import { goto } from '$app/navigation';
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	let files = $state(data.document?.files ?? []);

	async function deleteDocument(id: string) {
		await client.DELETE('/document/{id}', {
			params: { path: { id } }
		});
		goto('./');
	}

	async function upload(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const newfiles = formData.getAll('files') as File[];

		for (const file of newfiles) {
			const newFile = await client.POST('/document/{id}/upload', {
				params: { path: { id: data.document?.id as string } },
				body: { file: file as unknown as string },
				bodySerializer: (body) => {
					if (!body) return null;
					const formData = new FormData();
					formData.set('file', body.file);
					return formData;
				}
			});
			files.push(newFile.data ?? {});
		}
	}

	async function update(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const title = formData.get('title')?.toString() ?? '';
		const description = formData.get('description')?.toString() ?? '';
		const subject_id = Number(formData.get('subject_id')?.toString());
		const tags = formData.getAll('tags').map((id) => Number(id));
		const author = formData.get('author')?.toString() ?? '';

		await client.PATCH('/document/{id}', {
			params: { path: { id: data.document?.id as string } },
			body: {
				title,
				description,
				subject_id,
				tags,
				author
			}
		});

		goto('./');
	}
</script>

<h1>Document #{data.document?.id}</h1>

<p>
	Title : {data.document?.title}, description : {data.document?.description}, subject : {data
		.document?.subject?.name}, author: {data.document?.author?.firstname}
	{data.document?.author?.lastname}, tags : {data.document?.tags
		?.map((tag) => tag.name)
		.join(', ')},
</p>

<h4>Files</h4>

<ul>
	{#each files as file (file.id)}
		<li>
			<a target="_blank" href={`/files/${file.id}`}>{file.name}</a>
			<a href="/documents/{data.document?.id}/files/{file.id}"><button>Edit</button></a>
			<button
				onclick={async () => {
					await client.DELETE('/document/{id}/files/{fileID}', {
						params: {
							path: { id: data.document?.id as string, fileID: file.id as string }
						}
					});
					files = files.filter((f) => f.id !== file.id);
				}}
			>
				Delete
			</button>
		</li>
	{/each}
</ul>
<form onsubmit={upload}>
	<input type="file" name="files" multiple />
	<button type="submit">Upload</button>
</form>

<h3>Update</h3>

<form onsubmit={update}>
	<input type="text" name="title" value={data.document?.title} required />
	<input type="text" name="description" value={data.document?.description} required />
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
	<select name="author">
		{#each data.users as user (user.id)}
			<option value={user.id} selected={user.uid === data.me?.uid}>
				{user.firstname}
				{user.lastname}
			</option>
		{/each}
	</select>

	<button type="submit">update</button>
</form>

<h3>Delete</h3>

<button onclick={() => deleteDocument(data.document?.id ?? '')}>Delete</button>
<br />
<br />
<button onclick={() => goto('./')}> Go back </button>
