import * as S from '@/pages/Home/PopularArticle/PopularArticle.style';
import { useEffect, useRef, useState } from 'react';
import { useQuery } from 'react-query';
import { getPopularArticles } from '@/api/article';
import { convertIdxToArticleColorKey } from '@/utils/converter';

const PopularArticle = () => {
	const [currentIndex, setCurrentIndex] = useState(0);
	const mainArticleContent = useRef<HTMLDivElement>(null);
	const [mainContent, setMainContent] = useState(null);

	const { data, isSuccess, isError, isLoading } = useQuery('popular-articles', getPopularArticles);
	let articleList = [];
	let indexLimit = 0;

	useEffect(() => {
		if (isSuccess) {
			articleList = data.articles;
			indexLimit = data.articles.length;
			setCurrentIndex(0);
			console.log(articleList);
		}
	}, []);

	// useEffect(() => {
	// 	console.log('sss');
	// }, [currentIndex]);

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

	const getColorKey = (index: number) => {
		if (index < 0) {
			return convertIdxToArticleColorKey(9);
		}
		if (index > 9) {
			return convertIdxToArticleColorKey(0);
		}
		return convertIdxToArticleColorKey(index);
	};

	return (
		<S.Container>
			<S.LeftArrowButton onClick={handleLeftSlideEvent} />
			<S.LeftBackgroundArticle colorKey={getColorKey(currentIndex - 1)} />
			<S.ArticleContent colorKey={getColorKey(currentIndex)} ref={mainArticleContent}>
				<S.Title>
					Component를 어떻게 나누나요? 2줄 이상일때에 어떻게 할지 처리하기 위한
					곳djfkldsajfldsjflsdkjfljsdlfjskl
				</S.Title>
				<S.ArticleInfo>
					<S.ProfileBox>
						<S.UserImg
							src={'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg'}
						/>
						<S.UserName>자스민</S.UserName>
					</S.ProfileBox>
					<S.CommentBox>
						<S.CommentCount>12</S.CommentCount>
						<S.CommentIcon />
					</S.CommentBox>
				</S.ArticleInfo>
			</S.ArticleContent>
			<S.RightBackgroundArticle colorKey={getColorKey(currentIndex + 1)} />
			<S.RightArrowButton onClick={handleRightSlideEvent} />
		</S.Container>
	);
};

export default PopularArticle;
