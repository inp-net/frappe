<script lang="ts">
	import Header from '$components/Header.svelte';

	let { data } = $props();

	let user = data.me;
</script>

<Header title={`Bonjour ${user?.firstname}`} />
