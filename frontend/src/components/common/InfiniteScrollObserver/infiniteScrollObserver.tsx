import { useEffect, useRef } from 'react';

interface infiniteScrollObserverProps {
	children: React.ReactNode;
	hasNext: boolean;
	fetchNextPage: () => void;
}

const infiniteScrollObserver = ({
	children,
	hasNext,
	fetchNextPage,
}: infiniteScrollObserverProps) => {
	const endFlag = useRef<HTMLDivElement>(null);

	const intersectionObserver = new IntersectionObserver((entries) => {
		if (entries[0].isIntersecting && hasNext) {
			fetchNextPage();
		}
	});

	useEffect(() => {
		if (endFlag.current && hasNext) {
			intersectionObserver.observe(endFlag.current);
		}
		if (endFlag.current && !hasNext) {
			intersectionObserver.unobserve(endFlag.current);
		}
	}, [hasNext, endFlag.current]);

	return (
		<>
			{children}
			<div ref={endFlag} />
		</>
	);
};

export default infiniteScrollObserver;
