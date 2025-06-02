<script lang="ts">
	import type { PageProps } from './$types';

	let { data }: PageProps = $props();
</script>

<h1>Bonjour {data.me?.firstName}</h1>

<ul>
	<li>
		<a href="/admin/schools">Schools</a>
	</li>
	<li>
		<a href="/admin/majors">Majors</a>
	</li>
	<li>
		<a href="/admin/minors">Minors</a>
	</li>
	<li>
		<a href="/admin/teaching-units">Teaching Units</a>
	</li>
	<li>
		<a href="/admin/subjects">Subjects</a>
	</li>
	<li>
		<a href="/admin/tags">Tags</a>
	</li>
	<li>
		<a href="/admin/documents">Documents</a>
	</li>
</ul>
