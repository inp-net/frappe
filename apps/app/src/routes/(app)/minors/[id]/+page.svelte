<script lang="ts">
	import { goto } from '$app/navigation';
	import client from '$lib/api/client';
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();

	async function save(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const id = Number(formData.get('id')?.toString());
		const name = formData.get('name')?.toString() ?? '';
		const uid = formData.get('uid')?.toString() ?? '';
		const major_uid = formData.get('major_uid')?.toString() ?? '';
		const discontinued = formData.get('discontinued') !== null;

		await client.PATCH(`/minor/{id}`, {
			params: { path: { id } },
			body: {
				name: name ?? '',
				uid: uid ?? '',
				major_uid: major_uid ?? '',
				discontinued: discontinued ?? false
			}
		});

		goto('../');
	}

	async function deleteMajor(id: number) {
		await client.DELETE(`/minor/{id}`, {
			params: { path: { id } }
		});
		goto('../');
	}
</script>

<h1>Minor #{data.minor?.id}</h1>

<p>
	name : {data.minor?.name}, major : {data.minor?.major?.name}
</p>

<h3>Update</h3>

<form onsubmit={save}>
	<input type="hidden" name="id" value={data.minor?.id} />
	<input type="text" name="name" value={data.minor?.name} required />
	<button type="submit">Save</button>
</form>

<h3>Delete</h3>

<button onclick={() => deleteMajor(data.minor?.id ?? 0)}>Delete</button>
<br />
<br />
<button onclick={() => goto('../')}> Go back </button>
