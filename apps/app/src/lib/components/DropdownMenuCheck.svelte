<script lang="ts">
	import Icon from '@iconify/svelte';
	import { DropdownMenu, Label } from 'bits-ui';
	import { Checkbox } from 'bits-ui';

	let { name, data } = $props();

	let selecteds = $state(Array(data.length).fill(false));
</script>

<DropdownMenu.Root>
	<DropdownMenu.Trigger>
		{name}
		<Icon icon="heroicons:chevron-down-20-solid" />
	</DropdownMenu.Trigger>

	<DropdownMenu.Content>
		{#each data as dataItem, index (dataItem.id)}
			<DropdownMenu.CheckboxItem closeOnSelect={false}>
				<Checkbox.Root id={dataItem.id} bind:checked={selecteds[index]}>
					{#if selecteds[index]}
						<Icon icon="heroicons:check-20-solid" />
					{/if}
				</Checkbox.Root>
				<Label.Root for={dataItem.id}>{dataItem.name}</Label.Root>
			</DropdownMenu.CheckboxItem>
		{/each}
	</DropdownMenu.Content>
</DropdownMenu.Root>
