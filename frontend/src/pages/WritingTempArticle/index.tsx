import { useParams } from 'react-router-dom';

import WritingArticles from '@/pages/WritingArticles';

const WritingTempArticle = () => {
	const { id } = useParams();
	return <>{id && <WritingArticles tempId={Number(id)} />}</>;
};

export default WritingTempArticle;
