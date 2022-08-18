import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;

	width: 90%;
	height: ${({ theme }) => theme.size.SIZE_090};

	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};
	border-radius: ${({ theme }) => theme.size.SIZE_004};

	background-color: ${({ theme }) => theme.colors.GRAY_100};

	padding: ${({ theme }) => theme.size.SIZE_006};

	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const ArticleBox = styled.div`
	display: flex;
	align-items: center;
`;

export const ArticleTitle = styled.div`
	width: 80%;
	margin-left: ${({ theme }) => theme.size.SIZE_008};
	overflow: hidden;
	word-break: break-all;
	white-space: nowrap;
	text-overflow: ellipsis;
`;

export const ArticleCategory = styled.div<{ isQuestion: boolean }>`
	width: fit-content;
	height: fit-content;

	color: ${({ theme }) => theme.colors.WHITE};

	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};
	border-radius: ${({ theme }) => theme.size.SIZE_010};

	background-color: ${(props) => (props.isQuestion ? '#FF0063' : '#3AB0FF')};

	padding: ${({ theme }) => theme.size.SIZE_004};
`;

export const ContentLabel = styled.span`
	margin-right: ${({ theme }) => theme.size.SIZE_010};
`;

export const ContentBox = styled.div`
	overflow: hidden;
	word-break: break-all;
	white-space: wrap;

	padding: ${({ theme }) => theme.size.SIZE_002};
`;

export const CommentTime = styled.div`
	width: 100%;
	text-align: right;

	font-size: ${({ theme }) => theme.size.SIZE_012};
	color: ${({ theme }) => theme.colors.BLACK_400};
`;
