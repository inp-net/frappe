<script lang="ts">
	import client from '$lib/api/client';
	import type { PageProps } from './$types';
	import { goto } from '$app/navigation';

	let { data }: PageProps = $props();

	let teaching_units = $state(data.teaching_units);
	let majors = data.majors;
	let minors = data.minors;

	async function create(e: SubmitEvent) {
		e.preventDefault();
		const formData = new FormData(e.target as HTMLFormElement);
		const name = formData.get('name')?.toString() ?? '';
		const majors = (formData.getAll('majors_id') ?? []).map(Number);
		const minors = (formData.getAll('minors_id') ?? []).map(Number);

		const newTeachingUnit = await client.POST('/teachingunit/', {
			body: {
				name,
				majors,
				minors
			}
		});

		if (newTeachingUnit.data) {
			teaching_units.push(newTeachingUnit.data ?? {});
		}
	}
</script>

<h3>Liste des unités d'enseignement</h3>
<ul>
	{#each teaching_units as teaching_unit (teaching_unit.id)}
		<li>
			<a href={`/admin/teaching-units/${teaching_unit.id}`}
				>#{teaching_unit.id} - {teaching_unit.name}</a
			>
		</li>
	{/each}
</ul>

<h3>Créer une unité d'enseignement</h3>
<form class="form" onsubmit={create}>
	<input name="name" type="text" placeholder="Nom" required />
	<label for="majors_id">Choisir des majeures</label>
	<select name="majors_id" multiple>
		{#each majors as major (major.id)}
			<option value={major.id}>{major.name}</option>
		{/each}
	</select>
	<label for="minors_id">Choisir des mineures</label>
	<select name="minors_id" multiple>
		{#each minors as minor (minor.id)}
			<option value={minor.id}>{minor.name}</option>
		{/each}
	</select>
	<button type="submit">Create</button>
</form>

<button onclick={() => goto('./')}>Go back</button>

<style>
	.form {
		display: flex;
		flex-direction: column;
		gap: 0.5rem;
		width: 30rem;
	}
</style>
