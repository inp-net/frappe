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
	<li>
		<a href="/minors">Minors</a>
	</li>
	<li>
		<a href="/teaching-units">Teaching Units</a>
	</li>
	<li>
		<a href="/subjects">Subjects</a>
	</li>
	<li>
		<a href="/tags">Tags</a>
	</li>
	<li>
		<a href="/documents">Documents</a>
	</li>
</ul>
