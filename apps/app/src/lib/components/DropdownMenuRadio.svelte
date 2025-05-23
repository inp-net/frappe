<script lang="ts">
	import Icon from '@iconify/svelte';
	import { DropdownMenu, Label } from 'bits-ui';
	import { RadioGroup } from 'bits-ui';

	let { name, data } = $props();

	let selected = $state('');
</script>

<DropdownMenu.Root>
	<DropdownMenu.Trigger>
		{name}
		<Icon icon="heroicons:chevron-down-20-solid" />
	</DropdownMenu.Trigger>

	<DropdownMenu.Content>
		<RadioGroup.Root bind:value={selected}>
			{#each data as dataItem (dataItem.id)}
				<div class="items">
					<RadioGroup.Item id={dataItem.id} value={dataItem.id} />
					<Label.Root for={dataItem.id}>{dataItem.name}</Label.Root>
				</div>
			{/each}
		</RadioGroup.Root>
	</DropdownMenu.Content>
</DropdownMenu.Root>

<style lang="scss">
	.items {
		display: flex;
		align-items: center;
	}
</style>
