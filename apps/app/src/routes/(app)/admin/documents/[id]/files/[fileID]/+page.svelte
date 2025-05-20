<script lang="ts">
	import { goto } from '$app/navigation';
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	async function save(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const id = formData.get('id')?.toString() as string;
		const name = formData.get('name')?.toString() ?? '';

		await client.PATCH(`/document/file/{fileID}`, {
			params: { path: { fileID: id }, query: { name } }
		});
		goto('../');
	}
</script>

<h1>File #{data.file?.id}</h1>

<p>
	{data.file?.name} ({data.file?.extension})
</p>

<h3>Update</h3>
<form onsubmit={save}>
	<input type="hidden" name="id" value={data.file?.id} />
	<input type="text" name="name" value={data.file?.name} required />
	<button type="submit">Save</button>
</form>
<br />
<br />
<button onclick={() => goto('../')}> Go back </button>
