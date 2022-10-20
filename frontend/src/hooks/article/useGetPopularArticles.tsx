import { AxiosError } from 'axios';
import { useEffect, useRef, useState } from 'react';
import { useQuery } from 'react-query';

import { getPopularArticles, PopularArticles } from '@/api/article';
import * as S from '@/components/article/PopularArticle/PopularArticle.styles';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetPopularArticles = () => {
	const [currentIndex, setCurrentIndex] = useState(0);
	const [indexLimit, setIndexLimit] = useState(0);
	const mainArticleContent = useRef<HTMLDivElement>(null);

	const { data, error, isError, isSuccess, isLoading, isIdle } = useQuery<
		PopularArticles,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('popular-articles', getPopularArticles, { retry: 1, refetchOnWindowFocus: false });

	useThrowCustomError(isError, error);

	useEffect(() => {
		if (isSuccess) {
			setIndexLimit(data.articles.length);
			setCurrentIndex(0);
		}
	}, [isSuccess]);

	const handleClickLeftArrowButton = () => {
		if (currentIndex === 0) {
			// 애니메이션
			return;
		}
		setCurrentIndex(currentIndex - 1);
		mainArticleContent.current?.animate(S.showPopularSlider, S.animationTiming);
	};

	const handleClickRightArrowButton = () => {
		if (currentIndex === indexLimit - 1 || currentIndex === 9) {
			// 애니메이션
			return;
		}
		setCurrentIndex(currentIndex + 1);
		mainArticleContent.current?.animate(S.showPopularSlider, S.animationTiming);
	};

	return {
		data,
		isLoading,
		isIdle,
		isSuccess,
		currentIndex,
		handleClickLeftArrowButton,
		handleClickRightArrowButton,
		mainArticleContent,
	};
};

export default useGetPopularArticles;
