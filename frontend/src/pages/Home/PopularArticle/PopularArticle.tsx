import Loading from '@/components/common/Loading/Loading';
import ArticleItem from '@/pages/Home/ArticleItem/ArticleItem';
import * as S from '@/pages/Home/PopularArticle/PopularArticle.styles';
import useGetPopularArticles from '@/pages/Home/hooks/useGetPopularArticles';
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
