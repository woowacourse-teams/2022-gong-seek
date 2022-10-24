import { SingleTempArticleItemResponseType } from '@/api/tempArticle/tempArticleType';
import * as S from '@/components/tempArticle/TemporaryArticleItem/TemporaryArticleItem.styles';
import { categoryNameConverter, dateTimeConverter } from '@/utils/converter';

export interface TemporaryArticleItemProps {
	article: Omit<SingleTempArticleItemResponseType, 'id'>;
	onClick: () => void;
}

const TemporaryArticleItem = ({ article, onClick }: TemporaryArticleItemProps) => (
	<S.Container onClick={onClick}>
		<S.Title>{article.title}</S.Title>
		<S.SubInfo>
			<S.Category isQuestion={article.category === 'question'}>
				{categoryNameConverter(article.category)}
			</S.Category>
			<S.CreatedAt>{article.createdAt && dateTimeConverter(article.createdAt)}</S.CreatedAt>
		</S.SubInfo>
	</S.Container>
);

export default TemporaryArticleItem;
