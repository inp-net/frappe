<script lang="ts">
	import Icon from '@iconify/svelte';
	import { DropdownMenu } from 'bits-ui';
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
				<Checkbox.Root bind:checked={selecteds[index]}>
					{#if selecteds[index]}
						<Icon icon="heroicons:check-20-solid" />
					{/if}
				</Checkbox.Root>
				{dataItem.name}
			</DropdownMenu.CheckboxItem>
		{/each}
	</DropdownMenu.Content>
</DropdownMenu.Root>

<style lang="scss">
</style>
