<script lang="ts">
	import Icon from '@iconify/svelte';
	import { DropdownMenu, Label } from 'bits-ui';
	import { Checkbox } from 'bits-ui';

	let { name, data, selecteds = $bindable() } = $props();

	let initialSelecteds = Array.isArray(selecteds) ? selecteds : [selecteds];

	let checkeds = $state(
		Array(data.length)
			.fill(false)
			.map((_, index) => {
				return initialSelecteds.includes(data[index].id);
			})
	);

	$effect(() => {
		selecteds = checkeds
			.map((checked, index) => (checked ? data[index].id : null))
			.filter(Boolean);
	});
</script>

<DropdownMenu.Root>
	<DropdownMenu.Trigger>
		{name}
		<Icon icon="heroicons:chevron-down-20-solid" />
	</DropdownMenu.Trigger>

	<DropdownMenu.Content>
		{#each data as dataItem, index (dataItem.id)}
			<DropdownMenu.CheckboxItem closeOnSelect={false}>
				<Checkbox.Root id={dataItem.id} bind:checked={checkeds[index]}>
					{#if checkeds[index]}
						<Icon icon="heroicons:check-20-solid" />
					{/if}
				</Checkbox.Root>
				<Label.Root for={dataItem.id}>{dataItem.name}</Label.Root>
			</DropdownMenu.CheckboxItem>
		{/each}
	</DropdownMenu.Content>
</DropdownMenu.Root>
