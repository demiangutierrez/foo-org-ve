package pgsql;

import common.BlobRead;
import common.BlobWrite;
import common.InsertExample;
import common.ListAllExample;
import common.PreparedStatementExample;
import common.UpdateExample;

public class RunAll {

  public static void main(String[] args) throws Exception {
    CreateBlobTableExample.main(args);
    CreateTableExample.main(args);
    InsertExample.main(args);
    UpdateExample.main(args);
    ListAllExample.main(args);
    PreparedStatementExample.main(args);
    BlobWrite.main(args);
    BlobRead.main(args);
  }
}
