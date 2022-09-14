import Loading from '@/components/common/Loading/Loading';
import useGetPopularArticles from '@/hooks/article/useGetPopularArticles';
import ArticleItem from '@/pages/Home/ArticleItem/ArticleItem';
import * as S from '@/pages/Home/PopularArticle/PopularArticle.styles';
import { convertIdxToArticleColorKey } from '@/utils/converter';

const PopularArticle = () => {
	const {
		data,
		isLoading,
		isIdle,
		currentIndex,
		handleLeftSlideEvent,
		handleRightSlideEvent,
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

	if (!data?.articles.length) {
		return <S.EmptyText>텅 비었어요..!</S.EmptyText>;
	}

	return data ? (
		<S.Container>
			<S.LeftArrowButton onClick={handleLeftSlideEvent} />
			<S.LeftBackgroundArticle colorKey={getColorKey(currentIndex - 1)} />
			<S.ArticleContent colorKey={getColorKey(currentIndex)} ref={mainArticleContent}>
				<ArticleItem article={data.articles[currentIndex]} />
			</S.ArticleContent>
			<S.RightBackgroundArticle colorKey={getColorKey(currentIndex + 1)} />
			<S.RightArrowButton onClick={handleRightSlideEvent} />
		</S.Container>
	) : null;
};

export default PopularArticle;
