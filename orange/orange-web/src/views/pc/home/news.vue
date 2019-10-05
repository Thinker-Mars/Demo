<template>
    <Carousel autoplay :height="150" v-model="index" >
        <CarouselItem>
            <div class="demo-carousel"></div>
        </CarouselItem>
        <CarouselItem>
            <div class="demo-carousel"></div>
        </CarouselItem>
        <CarouselItem>
            <div class="demo-carousel"></div>
        </CarouselItem>
        <CarouselItem>
            <div class="demo-carousel"></div>
        </CarouselItem>
    </Carousel>
</template>

<script>
    export default {
        data () {
            return {
                index: 0
            }
        }
    }
</script>