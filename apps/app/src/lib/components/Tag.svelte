<script lang="ts">
	const stringToColor = (str: string) => {
		let hash = 0;
		str.split('').forEach((char) => {
			hash = char.charCodeAt(0) + ((hash << 5) - hash);
		});
		let color = '#';
		for (let i = 0; i < 3; i++) {
			const value = (hash >> (i * 8)) & 0xff;
			color += value.toString(16).padStart(2, '0');
		}
		return color;
	};

	let { tag } = $props();
	let color = stringToColor(tag.name);
</script>

<div style="color: oklch(from {color} var(--l) 0 h); background-color: {color};">
	{tag.name}
</div>

<style lang="scss">
	div {
		display: flex;
		justify-content: center;
		align-items: center;
		padding: 0.25rem 0.5rem;
		border-radius: 0.3rem;

		/* automatic color from background color */
		--l: clamp(0, (l / var(--l-threshold, 0.623) - 1) * -infinity, 1);
	}
</style>
