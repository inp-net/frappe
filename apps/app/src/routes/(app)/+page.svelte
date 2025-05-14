<script lang="ts">
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();
</script>

<h1>Bonjour {data.me?.firstName}</h1>

<ul>
	<li>
		<a href="/schools">Schools</a>
	</li>
	<li>
		<a href="/majors">Majors</a>
	</li>
</ul>
