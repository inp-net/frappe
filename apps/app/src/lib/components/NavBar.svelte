<script lang="ts">
	import Icon from '@iconify/svelte';
</script>

<nav>
	<div class="top">
		<a href="/">
			<Icon icon="heroicons:home" />
		</a>
	</div>
	<div class="bottom">
		<a href="/user">
			<Icon icon="heroicons:user-circle" />
		</a>
	</div>
</nav>

<style lang="scss">
	a {
		font-size: 1.2rem;
		color: #66686b;
	}

	nav {
		display: flex;
		position: fixed;
		flex-direction: column;
		justify-content: space-between;
		align-items: center;
		width: 3rem;
		height: 100dvh;
		padding: 1rem 0;
		box-sizing: border-box;
		background-color: #f8f9fa;
		border-right: solid 1px #e6e6e6;

		%top-bottom {
			display: flex;
			justify-content: center;
			width: 100%;
		}

		.top {
			@extend %top-bottom;
		}

		.bottom {
			@extend %top-bottom;
		}
	}
</style>
