import * as S from '@/pages/Home/PopularArticle/PopularArticle.style';
import { useEffect, useRef, useState } from 'react';
import { useQuery } from 'react-query';
import { getPopularArticles } from '@/api/article';
import { convertIdxToArticleColorKey } from '@/utils/converter';
import { useNavigate } from 'react-router-dom';

const PopularArticle = () => {
	const navigate = useNavigate();
	const [currentIndex, setCurrentIndex] = useState(0);
	const [indexLimit, setIndexLimit] = useState(0);

	const mainArticleContent = useRef<HTMLDivElement>(null);

	const { data, isSuccess, isError, isLoading, isIdle } = useQuery(
		'popular-articles',
		getPopularArticles,
	);

	useEffect(() => {
		if (isSuccess) {
			setIndexLimit(data.articles.length);
			setCurrentIndex(0);
		}
	}, [isSuccess]);

	if (isLoading || isIdle) {
		return <div>로딩중입니다</div>;
	}

	if (isError) {
		return <div>에러가 발생하였습니다</div>;
	}

	const navigateDetailPage = () => {
		navigate(`/articles/${data.articles[currentIndex].category}/${data.articles[currentIndex].id}`);
	};

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
				<S.Title onClick={navigateDetailPage}>{data?.articles[currentIndex].title}</S.Title>
				<S.ArticleInfo>
					<S.ProfileBox>
						<S.UserImg
							alt="유저의 프로필 이미지가 보여지는 곳 입니다 "
							src={data?.articles[currentIndex].author.avatarUrl}
						/>
						<S.UserName>{data?.articles[currentIndex].author.name}</S.UserName>
					</S.ProfileBox>
					<S.CommentBox>
						<S.CommentCount aria-label="댓글의 개수가 표시되는 곳입니다">
							{data?.articles[currentIndex].commentCount}
						</S.CommentCount>
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
