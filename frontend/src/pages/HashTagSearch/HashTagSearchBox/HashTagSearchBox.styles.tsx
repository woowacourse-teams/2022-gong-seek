import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 100%;
`;

export const HashTagPreviewBox = styled.div``;

export const HashTagLists = styled.div`
	display: flex;
	flex-wrap: wrap;

	width: 100%;

	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const HashTagItem = styled.button<{ isChecked: boolean }>`
	width: fit-content;
	height: fit-content;

	border: ${({ theme }) => theme.size.SIZE_001} solid;
	border-color: ${({ theme, isChecked }) =>
		isChecked ? theme.colors.PURPLE_500 : theme.colors.BLACK_400};
	border-radius: ${({ theme }) => theme.size.SIZE_004};

	background-color: transparent;

	padding: ${({ theme }) => theme.size.SIZE_004};

	color: ${({ isChecked, theme }) =>
		isChecked ? theme.colors.PURPLE_500 : theme.colors.BLACK_500};

	&:hover,
	&:active {
		cursor: pointer;
	}
`;

export const HashTagButton = styled.button`
	width: fit-content;
	height: fit-content;

	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.BLACK_400};
	border-radius: ${({ theme }) => theme.size.SIZE_004};
	padding: ${({ theme }) => theme.size.SIZE_004};

	background-color: transparent;

	&:hover,
	&:active {
		cursor: pointer;
	}
`;

export const EmptyMsg = styled.div``;
