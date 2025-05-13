<script lang="ts">
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	async function save(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const id = Number(formData.get('id')?.toString());
		const name = formData.get('name')?.toString() ?? '';
		const uid = formData.get('uid')?.toString() ?? '';

		await client.PATCH(`/school/{id}`, {
			params: { path: { id } },
			body: {
				name: name,
				uid: uid
			}
		});

		alert('School updated successfully');
	}
</script>

<h1>School #{data.school?.id}</h1>

<form onsubmit={save}>
	<input type="hidden" name="id" value={data.school?.id} />
	<input type="text" name="name" value={data.school?.name} required />
	<input type="text" name="uid" value={data.school?.uid} required />

	<button type="submit">Save</button>
</form>
