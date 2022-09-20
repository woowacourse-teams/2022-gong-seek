import * as S from '@/pages/TemporaryArticles/TemporaryArticleItem/TemporaryArticleItem.styles';
import { dateTimeConverter } from '@/utils/converter';

export interface TemporaryArticleItemProps {
	article: { title: string; createdAt: string };
	onClick: () => void;
}

const TemporaryArticleItem = ({ article, onClick }: TemporaryArticleItemProps) => (
	<S.Container onClick={onClick}>
		<S.Title>{article.title}</S.Title>
		<S.CreatedAt>{dateTimeConverter(article.createdAt)}</S.CreatedAt>
	</S.Container>
);

export default TemporaryArticleItem;
