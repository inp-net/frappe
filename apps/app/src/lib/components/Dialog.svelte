<script lang="ts">
	import type { Snippet } from 'svelte';
	import { Button, Dialog, type WithoutChild } from 'bits-ui';
	import Icon from '@iconify/svelte';

	type Props = Dialog.RootProps & {
		buttonText: string;
		buttonIcon: string;
		title: Snippet;
		description: Snippet;
		contentProps?: WithoutChild<Dialog.ContentProps>;
	};

	let {
		open = $bindable(false),
		children,
		buttonText,
		buttonIcon,
		contentProps,
		title,
		description,
		...restProps
	}: Props = $props();
</script>

<Dialog.Root bind:open {...restProps}>
	<Dialog.Trigger>
		<Icon icon={buttonIcon} />
		{buttonText}
	</Dialog.Trigger>
	<Dialog.Portal>
		<Dialog.Overlay />
		<Dialog.Content {...contentProps}>
			<Dialog.Title>
				{@render title()}
			</Dialog.Title>
			<Dialog.Description>
				{@render description()}
			</Dialog.Description>
			{@render children?.()}
			<Dialog.Close>
				<Button.Root><Icon icon="heroicons:x-mark-solid" /> Fermer</Button.Root>
			</Dialog.Close>
		</Dialog.Content>
	</Dialog.Portal>
</Dialog.Root>
