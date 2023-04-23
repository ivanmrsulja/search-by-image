<template>
    <ul>
        <li>
            <router-link :class="path == '/' ? 'active' : ''" to="/"
                >Search</router-link
            >
        </li>
        <li>
            <router-link :class="path == '/index' ? 'active' : ''" to="/index"
                >Index</router-link
            >
        </li>
    </ul>
</template>

<script>
    import { computed } from "vue";
    import { useRoute } from "vue-router";

    export default {
        name: "navbar",
        setup() {
            const route = useRoute();
            const path = computed(() => route.path);

            return { path };
        },
    };
</script>

<style scoped>
    ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #333;
        position: fixed;
        top: 0;
        width: 100%;
    }

    li {
        float: left;
    }

    li a {
        display: block;
        color: white;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
    }

    li a:hover:not(.active) {
        background-color: #111;
    }

    .active {
        background-color: #04aa6d;
    }
</style>
