import { AxiosError } from 'axios';
import { useEffect, useRef, useState } from 'react';
import { useQuery } from 'react-query';

import { getPopularArticles, PopularArticles } from '@/api/article';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import * as S from '@/pages/Home/PopularArticle/PopularArticle.styles';

const useGetPopularArticles = () => {
	const [currentIndex, setCurrentIndex] = useState(0);
	const [indexLimit, setIndexLimit] = useState(0);
	const mainArticleContent = useRef<HTMLDivElement>(null);

	const { data, error, isSuccess, isError, isLoading, isIdle } = useQuery<
		PopularArticles,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('popular-articles', getPopularArticles, { retry: false });

	useEffect(() => {
		if (isSuccess) {
			setIndexLimit(data.articles.length);
			setCurrentIndex(0);
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			if (!error.response) {
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
			);
		}
	}, [isError]);

	const handleLeftSlideEvent = () => {
		if (currentIndex === 0) {
			// 애니메이션
			return;
		}
		setCurrentIndex(currentIndex - 1);
		mainArticleContent.current?.animate(S.showPopularSlider, S.animationTiming);
	};

	const handleRightSlideEvent = () => {
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
		handleLeftSlideEvent,
		handleRightSlideEvent,
		mainArticleContent,
	};
};

export default useGetPopularArticles;
