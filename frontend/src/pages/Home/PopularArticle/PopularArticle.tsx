import Loading from '@/components/common/Loading/Loading';

import { convertIdxToArticleColorKey } from '@/utils/converter';
import useGetPopularArticles from '@/pages/Home/hooks/useGetPopularArticles';

import * as S from '@/pages/Home/PopularArticle/PopularArticle.styles';

const PopularArticle = () => {
	const {
		data,
		isLoading,
		isIdle,
		currentIndex,
		handleLeftSlideEvent,
		handleRightSlideEvent,
		navigateDetailPage,
		mainArticleContent,
	} = useGetPopularArticles();

	if (isLoading || isIdle) {
		return <Loading />;
	}

	const getColorKey = (index: number) => {
		if (index < 0) {
			return convertIdxToArticleColorKey(9);
		}
		if (index > 9) {
			return convertIdxToArticleColorKey(0);
		}
		return convertIdxToArticleColorKey(index);
	};

	return data ? (
		<S.Container>
			<S.LeftArrowButton onClick={handleLeftSlideEvent} />
			<S.LeftBackgroundArticle colorKey={getColorKey(currentIndex - 1)} />
			<S.ArticleContent colorKey={getColorKey(currentIndex)} ref={mainArticleContent}>
				<S.Title onClick={navigateDetailPage}>{data.articles[currentIndex].title}</S.Title>
				<S.ArticleInfo>
					<S.ProfileBox>
						<S.UserImg
							alt="유저의 프로필 이미지가 보여지는 곳 입니다 "
							src={data?.articles[currentIndex].author.avatarUrl}
						/>
						<S.UserName>{data.articles[currentIndex].author.name}</S.UserName>
					</S.ProfileBox>
					<S.CommentBox>
						<S.CommentCount aria-label="댓글의 개수가 표시되는 곳입니다">
							{data.articles[currentIndex].commentCount}
						</S.CommentCount>
						<S.CommentIcon />
					</S.CommentBox>
				</S.ArticleInfo>
			</S.ArticleContent>
			<S.RightBackgroundArticle colorKey={getColorKey(currentIndex + 1)} />
			<S.RightArrowButton onClick={handleRightSlideEvent} />
		</S.Container>
	) : null;
};

export default PopularArticle;
