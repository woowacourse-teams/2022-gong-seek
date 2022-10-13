import { useRef, useState } from 'react';

const useCarousel = () => {
	const [currentIndex, setCurrentIndex] = useState(0);
	const [indexLimit, setIndexLimit] = useState(0);
	const carouselElement = useRef<HTMLDivElement>(null);

	const showPopularSlider = [{ transform: `translateX(calc((-270px) * ${currentIndex}))` }];

	const animationTiming = {
		duration: 300,
		fill: 'forwards',
	} as const;

	const initCarousel = (maxArticleLength: number) => {
		setCurrentIndex(0);
		setIndexLimit(maxArticleLength);
	};

	const handleLeftSlideEvent = () => {
		if (currentIndex === 0) {
			// 애니메이션
			return;
		}
		setCurrentIndex(currentIndex - 1);
		carouselElement.current?.animate(showPopularSlider, animationTiming);
	};

	const handleRightSlideEvent = () => {
		if (currentIndex === indexLimit - 1 || currentIndex === 9) {
			// 애니메이션
			return;
		}
		setCurrentIndex(currentIndex + 1);
		carouselElement.current?.animate(showPopularSlider, animationTiming);
	};

	return {
		handleLeftSlideEvent,
		handleRightSlideEvent,
		initCarousel,
		carouselElement,
	};
};

export default useCarousel;
